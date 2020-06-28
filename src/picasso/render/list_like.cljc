(ns picasso.render.list-like
  "equivalent to pinkgorilla.ui.renderer, but for clojurescript
   renders clojurescript data structure to html"
  (:require
   [clojure.string :as string]
   [picasso.protocols :refer [paint]]
   [picasso.protocols :refer [make Renderable render]]))

(defn list-like [{:keys [class open close sep] :as options} items]
  (make :list-like
        (merge options {:items (map render items)})))

(defmethod paint :list-like [{:keys [class open close sep items #_content]}]
  ;(let [{:keys [class open close sep items]} content]
  (paint (make
          :hiccup
          [:div
           [:span.font-bold.teal-700.mr-1 open]
           (into [:span {:class class}]
                 (map paint items))
           [:span.font-bold.teal-700.ml-1 close]])))

