(ns statute.facts
  "General-law compliance catalog for Zambia (ZMB) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company operating in this jurisdiction must generally track for
  compliance. Mirrors cloud-itonami-iso3166-jpn/-deu/-bgr/-aze/-alb/
  -arm/-atg/-ben/-btn/-bwa/-caf/-est/-gmb/-lso/-nam's `statute.facts`
  (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL government-adjacent URL -- never
  fabricated.

  Two entries below were confirmed the SAME way: this iteration fetched
  the Zambia Legal Information Institute's (ZambiaLII, `zambialii.org`)
  own filtered legislation-listing table directly
  (`zambialii.org/legislation/?natures=act&years=<year>`, plain
  server-rendered HTML, HTTP 200, NOT behind any bot-challenge -- unlike
  several prior siblings' own individual-document routes on their
  respective national LIIs) and read the exact row title + citation
  column string for each Act. Confidence is HIGH on each citation's
  exact title/act-number (independently read from ZambiaLII's own
  listing table's dedicated citation column, corroborated a second way
  for the Companies Act via PACRA's own service-information microsite
  naming 'Companies Form 3').

  - Company/commercial-entity law: 'Companies Act, 2017' -- confirmed
    directly from ZambiaLII's own filtered listing
    (`zambialii.org/legislation/?natures=act&years=2017`), row title
    'Companies Act, 2017', own citation column 'Act 10 of 2017'. PACRA's
    own service-information microsite (`info.pacra.org.zm`, fetched
    directly) independently names the registration instrument this Act
    requires: 'a completed and signed Application for Incorporation of a
    company (Companies Form 3)'.
  - Labour law: 'Employment Code Act, 2019' -- confirmed directly from
    ZambiaLII's own filtered listing
    (`zambialii.org/legislation/?natures=act&years=2019`), row title
    'Employment Code Act, 2019', own citation column 'Act 3 of 2019'.
    ZambiaLII's SAME listing also separately names 'Employment Code
    (Exemption) Regulations, 2020' as a related subsidiary instrument --
    this iteration cites only the primary Act here, the same discipline
    this family's Namibia (NAM) sibling's catalog uses when a listing
    surfaces multiple related instruments without confirming their
    exact relationship.
  - Tax law: 'Income Tax Act' (Chapter 323 of the Laws of Zambia) --
    this iteration did NOT independently fetch the Income Tax Act's own
    primary statutory text or ZRA's own citation of its chapter number
    (an honest gap); the citation used here comes from a SEPARATE
    official primary source this iteration DID fetch and read directly
    -- the Investment, Trade and Business Development Act, 2022 (Act No.
    18 of 2022, fetched directly from ZambiaLII), whose own text reads
    verbatim: '...recommend to the Minister responsible for finance that
    tax incentives be granted to the investor under the Income Tax Act
    or the ... [Customs and Excise Act] ... remit the taxes due... for
    the charge year in which the incentive is accessed or accrued. [
    Cap. 323 ; Cap. 322 ]' -- confirming the Income Tax Act's own
    chapter number (323) from a genuine official cross-reference, the
    same honest-secondary-sourcing discipline the NAM sibling's catalog
    used for its own Companies Act, 2004 citation. This iteration did
    NOT find a dedicated Value Added Tax Act entry via this same
    cross-reference route -- an honest gap, not modeled below (ZRA's own
    site names 'Value Added Tax' as one of its administered tax types,
    zra.org.zm/business-registration-requirements, fetched directly, but
    this iteration did not independently confirm a chapter/act-number
    citation for it, so it is not catalogued here as a separate entry).

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit. ZMB's catalog has 3
  entries -- the Income Tax Act citation had to be independently sourced
  from the Investment, Trade and Business Development Act, 2022's own
  cross-reference text rather than ZambiaLII's own listing directly (an
  honest, narrower sourcing path than the Companies Act / Employment
  Code Act entries, disclosed above and in each entry's own
  `:statute/law-number` note)."
  {"ZMB"
   [{:statute/id "zmb.companies-act-2017"
     :statute/title "Companies Act, 2017"
     :statute/jurisdiction "ZMB"
     :statute/kind :law
     :statute/law-number "Companies Act, 2017 (Act 10 of 2017) -- title, year and Act number confirmed directly from the Zambia Legal Information Institute's (ZambiaLII) own filtered legislation-listing table (zambialii.org/legislation/?natures=act&years=2017, plain server-rendered HTML fetched directly, HTTP 200), row title 'Companies Act, 2017', own citation column 'Act 10 of 2017'. PACRA's own service-information microsite independently names the registration instrument this Act requires ('Companies Form 3', info.pacra.org.zm/what-is-a-local-company/, fetched directly), corroborating that this Act is genuinely PACRA's own operative law rather than an unrelated listing match"
     :statute/url "https://zambialii.org/akn/zm/act/2017/10/eng@2017-11-20"
     :statute/url-provenance :official-zambialii-org
     :statute/enacted-date "2017"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance}}
    {:statute/id "zmb.employment-code-act-2019"
     :statute/title "Employment Code Act, 2019"
     :statute/jurisdiction "ZMB"
     :statute/kind :law
     :statute/law-number "Employment Code Act, 2019 (Act 3 of 2019) -- title, year and Act number confirmed directly from ZambiaLII's own filtered legislation-listing table (zambialii.org/legislation/?natures=act&years=2019, fetched directly, HTTP 200), row title 'Employment Code Act, 2019', own citation column 'Act 3 of 2019'. This iteration did not independently fetch the Act's own primary statutory text, only ZambiaLII's own citation of its title/year/number, so no section numbers are cited here"
     :statute/url "https://zambialii.org/akn/zm/act/2019/3/eng@2019-04-12"
     :statute/url-provenance :official-zambialii-org
     :statute/enacted-date "2019"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:labor}}
    {:statute/id "zmb.income-tax-act-cap-323"
     :statute/title "Income Tax Act"
     :statute/jurisdiction "ZMB"
     :statute/kind :law
     :statute/law-number "Income Tax Act (Chapter 323 of the Laws of Zambia) -- confirmed directly from the Investment, Trade and Business Development Act, 2022 (Act No. 18 of 2022, fetched directly as full text from ZambiaLII, zambialii.org/akn/zm/act/2022/18/eng@2024-04-18), own text: '...taxes due... or Customs and Excise Act, for the charge year in which the incentive is accessed or accrued. [ Cap. 323 ; Cap. 322 ]'. This iteration did not independently fetch the Income Tax Act's own primary statutory text or a dedicated ZambiaLII listing row for it (an honest gap -- a genuine but SECONDARY official cross-reference source, the same discipline the NAM sibling's catalog used for its own Companies Act, 2004 citation), so no section numbers are cited here. No Value Added Tax Act entry is modeled -- an honest gap, not independently confirmed via this same cross-reference route this iteration"
     :statute/url "https://zambialii.org/akn/zm/act/2022/18/eng@2024-04-18"
     :statute/url-provenance :official-zambialii-org-cross-reference
     :statute/enacted-date nil
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:tax}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-zmb statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "ZMB")) " ZMB statute(s) seeded with an "
                 "official title/year/act-number/URL citation (the Income Tax Act "
                 "entry sourced via a secondary official cross-reference rather "
                 "than a direct ZambiaLII listing row -- an honest gap, see "
                 "namespace docstring). Extend `statute.facts/catalog`, never "
                 "fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :tax)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
