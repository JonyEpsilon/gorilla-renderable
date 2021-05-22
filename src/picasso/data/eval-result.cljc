(ns picasso.data.eval-result)

(def clj-hiccup
  {:id :7c9ab23f-c32f-4879-b74c-de7835ca8ba4
   :picasso {:type :hiccup
             :content [:span {:class "clj-long"} 13]}})

(def clj-list-like
  {:id :77abc2fd-3533-4435-ab50-d1cb222c06d3
   :picasso {:type :list-like
             :content {:class "clj-vector" 
                       :open [, :close ]
                       :separator  "," 
                       :items [{:type :hiccup
                                :content [:span {:class "clj-long"} 7]} 
                               {:type :hiccup
                                :content [:span {:class "clj-long"} 8]}]
                       :value [7 8]}}})
