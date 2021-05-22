(ns picasso.data.paint)

(def hiccup
  {:type :hiccup
   :content [:span {:class "clj-long"} "1"]})

(def list-like
  {:type :list-like
   :content {:class "clj-vector"
             :open "["
             :close "]"
             :separator " "
             :items [{:type :hiccup
                      :content [:span {:class "clj-long"} "1"]}
                     {:type :hiccup
                      :content [:span {:class "clj-long"} "2"]}
                     {:type :hiccup
                      :content [:span {:class "clj-long"} "3"]}]}})
