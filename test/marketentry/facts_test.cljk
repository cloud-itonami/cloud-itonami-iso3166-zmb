(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest zmb-has-spec-basis
  (let [sb (facts/spec-basis "ZMB")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/subcontracting-mandate-spec-basis "ZMB")))))

(deftest zmb-rep-spec-basis-is-honestly-absent
  (testing "no Zambia-specific representative/director exclusion-extension provision was confirmed this iteration -- deliberately not claimed"
    (is (nil? (facts/rep-spec-basis "ZMB")))))

(deftest zmb-corporate-number-spec-basis-is-honestly-absent
  (testing "the ZRA TPIN scheme is confirmed (see required-evidence), but no section-level statutory citation for it was independently confirmed this iteration -- deliberately not claimed as a dedicated corporate-number spec-basis"
    (is (nil? (facts/corporate-number-spec-basis "ZMB")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "ZMB")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "ZMB" all)))
    (is (not (facts/required-evidence-satisfied? "ZMB" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["ZMB" "ATL"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ZMB"] (:covered-jurisdictions c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))

(deftest subcontracting-mandate-spec-basis-threshold-matrix
  (let [sm (facts/subcontracting-mandate-spec-basis "ZMB")]
    (is (= 1000000.0 (get-in sm [:subcontracting-mandate-threshold-matrix :goods-and-non-consulting-services])))
    (is (= 1000000.0 (get-in sm [:subcontracting-mandate-threshold-matrix :works])))
    (is (= 600000.0 (get-in sm [:subcontracting-mandate-threshold-matrix :consulting-services])))
    (is (= 20.0 (:subcontracting-mandate-percentage sm)))))
