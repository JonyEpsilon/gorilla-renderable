(ns demo
  (:require
   [picasso.default-config]
   [picasso.protocols :refer [paint render]]))

(render 1)

(paint {:type :hiccup
        :content [:span {:class "clj-long"} "1"]})

(paint
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
