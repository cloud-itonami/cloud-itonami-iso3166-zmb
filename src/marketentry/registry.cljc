(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `subcontracting-mandate-applies?` / `subcontracting-matches-claim?` /
  `subcontracting-mismatch-claim?` are the SAME discipline applied to a
  genuinely Zambia-specific mechanism: the Public Procurement
  Regulations, 2022 (a Statutory Instrument made by the Minister of
  Finance and National Planning under the Public Procurement Act, 2020,
  Act No. 8 of 2020, fetched directly as a PDF from
  zppa.org.zm/procurement-legislations-and-handbooks), Regulation 20(1),
  which reads verbatim: 'A procuring entity shall require foreign
  bidders or suppliers to subcontract to citizen bidders and suppliers,
  a minimum threshold of twenty percent of the total value of the
  procurement for procurements exceeding the simplified bidding and
  simplified selection thresholds set out in the Second Schedule.' The
  Second Schedule itself ('THRESHOLDS FOR PROCUREMENT METHODS',
  Regulations 20/26/29/38/39/40/70/184) publishes the exact Simplified
  Bidding / Simplified Selection ceiling per contract type: Goods and
  Non-Consulting Services and Works, up to K1,000,000.00; Consulting
  Services, up to K600,000.00 -- reproduced verbatim in
  `subcontracting-threshold-matrix` below.

  This is a GENUINELY DIFFERENT check SHAPE than this family's Namibia
  (NAM) sibling's own flagship: NAM's CPBN Board-routing mechanism is a
  TWO-DIMENSIONAL (public-entity-category x contract-type) CEILING
  LOOKUP whose exceeded-threshold consequence is a PROCEDURAL ROUTING
  change (who conducts the bid). Zambia's mechanism is a
  SINGLE-DIMENSION (contract-type only) THRESHOLD GATE whose
  exceeded-threshold consequence, for a FOREIGN bidder only, is a FLAT
  MINIMUM-PERCENTAGE SET-ASIDE MANDATE (subcontract >= 20% of the
  procurement's value to citizen bidders/suppliers) -- testing the
  bidder's own declared compliance with a quota, not a procedural
  question about who runs the process. The engagement declares its own
  origin (`:bidder-origin`), the procurement's own contract-type and
  estimated value, its own PROPOSED citizen-subcontract percentage, and
  its own belief about whether that proposal is compliant
  (`:claimed-subcontracting-compliant?`); the governor independently
  recomputes the true compliance from the published Regulation 20 /
  Second Schedule figures and compares for EQUALITY against the claim --
  catching an operator who understates a procurement's estimated value,
  misclassifies its contract type, or proposes a subcontract percentage
  below the mandated 20% floor while claiming compliance.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal. It builds the RECORD an
  operator would keep, not the act of submitting a portal registration
  itself (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(def subcontracting-threshold-matrix
  "Public Procurement Regulations, 2022 (Statutory Instrument made by
  the Minister of Finance and National Planning under the Public
  Procurement Act, 2020, Act No. 8 of 2020, fetched directly as a PDF),
  Second Schedule ('THRESHOLDS FOR PROCUREMENT METHODS', Regulations
  20, 26, 29, 38, 39, 40, 70 and 184): the Simplified Bidding /
  Simplified Selection ceiling (Zambian Kwacha, K) per contract type,
  ABOVE which a foreign bidder or supplier becomes subject to the
  Regulation 20(1) citizen-subcontracting mandate."
  {:goods-and-non-consulting-services 1000000.0
   :works 1000000.0
   :consulting-services 600000.0})

(def subcontracting-mandate-percentage
  "Regulation 20(1) of the Public Procurement Regulations, 2022, own
  text: 'a minimum threshold of twenty percent of the total value of
  the procurement' -- the flat minimum citizen-subcontract percentage a
  foreign bidder/supplier must meet once its declared procurement
  exceeds the applicable `subcontracting-threshold-matrix` ceiling."
  20.0)

(defn compute-subcontracting-mandate-applies?
  "The ground-truth determination for whether the Regulation 20(1)
  citizen-subcontracting mandate applies to `bidder-origin`/
  `contract-type`/`estimated-value`, independently recomputed from the
  Second Schedule's own published thresholds: `true` only when
  `bidder-origin` is `:foreign` AND `estimated-value` EXCEEDS the
  published threshold for that contract type (the mandate is, by its
  own text, a duty imposed on foreign bidders/suppliers only -- a
  citizen bidder is never subject to it). An unrecognized `contract-type`
  simply returns nil -- never guesses a threshold outside the published
  matrix."
  [bidder-origin contract-type estimated-value]
  (when-let [threshold (get subcontracting-threshold-matrix contract-type)]
    (boolean (and (= bidder-origin :foreign)
                  (> (double estimated-value) threshold)))))

(defn compute-required-subcontract-percentage
  "The ground-truth minimum citizen-subcontract percentage required for
  `bidder-origin`/`contract-type`/`estimated-value`: `subcontracting-
  mandate-percentage` (20.0) when the mandate applies, `0.0` when it
  does not (no minimum required), or nil for an unrecognized
  contract-type (never guesses)."
  [bidder-origin contract-type estimated-value]
  (when-some [applies? (compute-subcontracting-mandate-applies?
                         bidder-origin contract-type estimated-value)]
    (if applies? subcontracting-mandate-percentage 0.0)))

(defn subcontracting-matches-claim?
  "Does `engagement`'s own `:claimed-subcontracting-compliant?` equal
  the INDEPENDENTLY recomputed compliance determination -- whether its
  own `:proposed-subcontract-percentage` meets or exceeds the
  ground-truth required percentage for its own declared
  `:bidder-origin`/`:contract-type`/`:estimated-value`? An unrecognized
  contract-type fails closed (does not throw, never guesses). NOTE:
  this function uses `some?`, not `when-let`/`if-let`, to distinguish a
  legitimately computed `false` requirement (e.g. a citizen bidder, or a
  foreign bidder within threshold -- required percentage 0.0, trivially
  met) from the nil 'unrecognized contract-type' case."
  [{:keys [bidder-origin contract-type estimated-value
           proposed-subcontract-percentage claimed-subcontracting-compliant?]}]
  (let [required (compute-required-subcontract-percentage
                   bidder-origin contract-type estimated-value)]
    (boolean (and (some? required)
                  (= (boolean claimed-subcontracting-compliant?)
                     (>= (double (or proposed-subcontract-percentage 0.0)) required))))))

(defn subcontracting-mismatch-claim?
  "Does `engagement` declare `:seeking-subcontracting-determination?
  true` (i.e. it is asking this actor to confirm whether its own
  proposed citizen-subcontract percentage satisfies Regulation 20(1) for
  its own declared procurement) while the INDEPENDENTLY recomputed
  `subcontracting-matches-claim?` is false? An engagement not seeking a
  subcontracting determination is never flagged by this check
  (entity/engagement-scope-gated, the same discipline this family's
  Namibia (NAM) sibling's `:seeking-board-routing-determination?`-gated
  check uses)."
  [{:keys [seeking-subcontracting-determination?] :as engagement}]
  (boolean (and seeking-subcontracting-determination?
                (not (subcontracting-matches-claim? engagement)))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
