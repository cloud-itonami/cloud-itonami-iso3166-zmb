(ns culture.facts
  "Country-level regional-culture catalog for Zambia (ZMB) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).
  The heritage entry (Victoria Falls / Mosi-oa-Tunya) is a border site
  shared with Zimbabwe (ZWE); stated honestly per the cited source.

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"ZMB"
   [{:culture/id "zmb.dish.nshima"
     :culture/name "Nshima"
     :culture/country "ZMB"
     :culture/kind :dish
     :culture/summary "Staple food of Zambia made from maize flour and water boiled into a thick porridge (nsima/ubwali), stirred with a flat wooden spoon (m'tiko/umwiko) and eaten with a 'relish' side dish."
     :culture/url "https://en.wikipedia.org/wiki/Ugali"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "zmb.dish.chikanda"
     :culture/name "Chikanda"
     :culture/country "ZMB"
     :culture/kind :dish
     :culture/summary "Zambian dish made from boiled root tubers of terrestrial orchids mixed with groundnut meal, nicknamed 'African polony' for its bologna-like texture; culturally significant to the Bemba people of northeast Zambia and traditionally served at weddings."
     :culture/url "https://en.wikipedia.org/wiki/Chikanda"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "zmb.dish.ifisashi"
     :culture/name "Ifisashi"
     :culture/country "ZMB"
     :culture/kind :dish
     :culture/summary "Traditional Zambian dish of leafy greens (such as pumpkin, sweet potato or spinach leaves) cooked with ground roasted peanuts, onion and tomato, called ifisashi in Bemba and visashi in Chinyanja, typically served with nshima."
     :culture/url "https://en.wikipedia.org/wiki/Ifisashi"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "zmb.beverage.munkoyo"
     :culture/name "Munkoyo"
     :culture/country "ZMB"
     :culture/kind :beverage
     :culture/summary "Fermented beer-like beverage brewed in rural Zambia from maize porridge and pounded Rhynchosia venulosa roots, an important source of rural employment and fermented primarily by lactic acid bacteria."
     :culture/url "https://en.wikipedia.org/wiki/Munkoyo"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "zmb.festival.kuomboka"
     :culture/name "Kuomboka"
     :culture/country "ZMB"
     :culture/kind :festival
     :culture/summary "Traditional annual ceremony of the Lozi people of Zambia in which the Litunga (king) relocates from his lowland palace to higher ground during seasonal flooding, marked by elaborate processions with ceremonial barges and royal drummers."
     :culture/url "https://en.wikipedia.org/wiki/Kuomboka"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "zmb.heritage.victoria-falls"
     :culture/name "Victoria Falls"
     :culture/name-local "Mosi-oa-Tunya"
     :culture/country "ZMB"
     :culture/kind :heritage
     :culture/summary "Waterfall on the Zambezi River located on the border between Zambia and Zimbabwe, inscribed as the UNESCO World Heritage Site 'Mosi-oa-Tunya / Victoria Falls' in 1989; Zambia's national park on its side of the falls is named Mosi-oa-Tunya."
     :culture/url "https://en.wikipedia.org/wiki/Victoria_Falls"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-zmb culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "ZMB"))
                 " ZMB entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
