(ns picasso.paint.list-like
  (:require
   [picasso.protocols :refer [paint]]))

 ; picasso spec requires :type and :content
  ; but initial gorilla list-alike converter didnt obey this,
  ; and supplied the options directly.


(defn box [class open close inside]
  [:span {:class class}
   [:span.font-bold.teal-700.mr-1 open]
   (into [:span.items] inside)
   [:span.font-bold.teal-700.ml-1 close]])

(defmethod paint :list-like
  [{:keys [#_type content] :as picasso-spec}]
  (let [{:keys [class open close separator items]} content]
    (box class open close
         (->> (map paint items)
                ;(interpose [:span separator])
              ))))
