(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest zmb-has-spec-basis
  (let [sb (facts/spec-basis "ZMB")]
    (is (= 3 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["ZMB" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["zmb.employment-code-act-2019"]
         (mapv :statute/id (facts/by-topic "ZMB" :labor))))
  (is (= ["zmb.income-tax-act-cap-323"]
         (mapv :statute/id (facts/by-topic "ZMB" :tax))))
  (is (= ["zmb.companies-act-2017"]
         (mapv :statute/id (facts/by-topic "ZMB" :corporate-governance))))
  (is (empty? (facts/by-topic "ATL" :labor))))
