(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "ZMB" 0)
        s (registry/register-submit "eng-1" "ZMB" 0)]
    (is (= "ZMB-DFT-000000" (get d "draft_number")))
    (is (= "ZMB-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "ZMB" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest subcontracting-threshold-lookup
  (testing "each contract-type cell resolves to its own exact Second Schedule threshold"
    (is (= 1000000.0 (get registry/subcontracting-threshold-matrix :goods-and-non-consulting-services)))
    (is (= 1000000.0 (get registry/subcontracting-threshold-matrix :works)))
    (is (= 600000.0 (get registry/subcontracting-threshold-matrix :consulting-services)))
    (is (= 20.0 registry/subcontracting-mandate-percentage))))

(deftest compute-subcontracting-mandate-applies
  (testing "a foreign bidder strictly exceeding the published threshold is subject to the mandate"
    (is (true? (registry/compute-subcontracting-mandate-applies? :foreign :goods-and-non-consulting-services 1500000.0)))
    (is (false? (registry/compute-subcontracting-mandate-applies? :foreign :goods-and-non-consulting-services 1000000.0))
        "exactly at the threshold is not 'exceeding' it")
    (is (false? (registry/compute-subcontracting-mandate-applies? :foreign :goods-and-non-consulting-services 500000.0))))
  (testing "a citizen bidder is never subject to the mandate, regardless of value"
    (is (false? (registry/compute-subcontracting-mandate-applies? :citizen :goods-and-non-consulting-services 5000000.0))))
  (testing "an unrecognized contract-type never guesses a threshold"
    (is (nil? (registry/compute-subcontracting-mandate-applies? :foreign :unknown-type 1.0)))))

(deftest compute-required-subcontract-percentage-shape
  (is (= 20.0 (registry/compute-required-subcontract-percentage :foreign :works 1500000.0)))
  (is (= 0.0 (registry/compute-required-subcontract-percentage :foreign :works 500000.0)))
  (is (= 0.0 (registry/compute-required-subcontract-percentage :citizen :works 5000000.0)))
  (is (nil? (registry/compute-required-subcontract-percentage :foreign :unknown-type 1.0))))

(deftest subcontracting-matches-claim
  (testing "a claim that EXACTLY matches the independently recomputed compliance matches"
    (is (true? (registry/subcontracting-matches-claim?
                {:bidder-origin :foreign :contract-type :goods-and-non-consulting-services
                 :estimated-value 1500000.0 :proposed-subcontract-percentage 25.0
                 :claimed-subcontracting-compliant? true})))
    (is (true? (registry/subcontracting-matches-claim?
                {:bidder-origin :citizen :contract-type :works
                 :estimated-value 5000000.0 :proposed-subcontract-percentage 0.0
                 :claimed-subcontracting-compliant? true}))
        "a citizen bidder trivially meets the (zero) requirement"))
  (testing "a claim opposite to the independently recomputed compliance does not match"
    (is (false? (registry/subcontracting-matches-claim?
                 {:bidder-origin :foreign :contract-type :goods-and-non-consulting-services
                  :estimated-value 1500000.0 :proposed-subcontract-percentage 10.0
                  :claimed-subcontracting-compliant? true}))
        "K1.5M exceeds the K1M goods threshold -- 20% minimum required, 10% proposed is short, claim of true is wrong"))
  (testing "missing or unrecognized contract-type fails closed"
    (is (false? (registry/subcontracting-matches-claim?
                 {:bidder-origin :foreign :estimated-value 1.0
                  :proposed-subcontract-percentage 100.0 :claimed-subcontracting-compliant? true})))
    (is (false? (registry/subcontracting-matches-claim?
                 {:bidder-origin :foreign :contract-type :unknown-type
                  :estimated-value 1.0 :proposed-subcontract-percentage 100.0
                  :claimed-subcontracting-compliant? true})))))

(deftest subcontracting-mismatch-claim-is-entity-scope-gated
  (testing "an engagement NOT seeking a subcontracting determination is never flagged, even if the claim is wrong"
    (is (false? (registry/subcontracting-mismatch-claim?
                 {:seeking-subcontracting-determination? false
                  :bidder-origin :foreign :contract-type :goods-and-non-consulting-services
                  :estimated-value 1500000.0 :proposed-subcontract-percentage 10.0
                  :claimed-subcontracting-compliant? true}))))
  (testing "a determination-seeking engagement whose claim does NOT match the independently recomputed compliance -> mismatch"
    (is (true? (registry/subcontracting-mismatch-claim?
                {:seeking-subcontracting-determination? true
                 :bidder-origin :foreign :contract-type :goods-and-non-consulting-services
                 :estimated-value 1500000.0 :proposed-subcontract-percentage 10.0
                 :claimed-subcontracting-compliant? true}))))
  (testing "a determination-seeking engagement whose claim DOES match -> not flagged"
    (is (false? (registry/subcontracting-mismatch-claim?
                 {:seeking-subcontracting-determination? true
                  :bidder-origin :foreign :contract-type :goods-and-non-consulting-services
                  :estimated-value 1500000.0 :proposed-subcontract-percentage 25.0
                  :claimed-subcontracting-compliant? true})))))
