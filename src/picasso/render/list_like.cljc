(ns picasso.render.list-like
  "equivalent to pinkgorilla.ui.renderer, but for clojurescript
   renders clojurescript data structure to html"
  (:require
   [clojure.string :as string]
   [picasso.protocols :refer [paint]]
   [picasso.protocols :refer [make Renderable render]]))

(defn list-like [options items]
  (make :list-like
        (merge options {:items (map render items)})))

(defn paint-list-alike [{:keys [content]}]
  (let [{:keys [class open close separator items]} content
        paint-sep (fn [i]
                    [:span (paint i) separator])]
    (paint (make
            :hiccup
            [:span
             [:span.font-bold.teal-700.mr-1 open]
             (into [:span {:class class}]
                   (map paint-sep items))
             [:span.font-bold.teal-700.ml-1 close]]))))

(defmethod paint :list-like [{:keys [content] :as picasso-spec}]
  ; picasso spec requires :type and :content
  ; but initial gorilla list-alike converter didnt obey this,
  ; and supplied the options directly.
  (if content
    (paint-list-alike content)
    (paint-list-alike {:content (dissoc picasso-spec :type)})))

