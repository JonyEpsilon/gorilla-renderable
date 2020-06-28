(ns picasso.render.list-alike
  "equivalent to pinkgorilla.ui.renderer, but for clojurescript
   renders clojurescript data structure to html"
  (:require
   [clojure.string :as string]
   [picasso.protocols :refer [paint]]
   [picasso.protocols :refer [make Renderable render]]))

(defn list-alike [{:keys [class open close sep] :as options} self]
  (make :list-alike
        (merge options {:items self})))

(defmethod paint :list-alike [{:keys [class open close sep items #_content]}]
  ;(let [{:keys [class open close sep items]} content]
  (paint (make
          :hiccup
          [:div
           [:span.font-bold.teal-700.mr-1 open]
           (into [:span {:class class}]
                 (map render items))
           [:span.font-bold.teal-700.ml-1 close]])))

