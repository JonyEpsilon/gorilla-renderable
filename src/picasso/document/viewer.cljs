(ns picasso.document.viewer
  (:require 
   [re-frame.core :as rf]
   [picasso.document.rf]))

(defn render-unknown [{:keys [id type data state] :as seg}]
  [:div.render-unknown
   [:p "segment id:" id "type " type]
   [:p "data: " (pr-str data)]
   [:p "state: " (pr-str state)]
   ])

(defn seg-view [{:keys [md-view code-view] :as opts} {:keys [id type data state] :as seg}]
  (let [render (case type
                 :code code-view
                 :md md-view)]
     (if render
       [render id data state]
       [render-unknown seg])))

(defn doc-view
  [ opts {:keys [id meta segments] :as document}]
  (let [seg-view (partial seg-view opts)]
    [:div
     [:p "doc id:" id]
     [:p "meta: " (pr-str meta)]]
    (into [:div] (map seg-view segments))))

(defn doc-view-id [opts id]
  (let [doc (rf/subscribe [:doc/view id] )]
    (fn [opts id]
      [doc-view opts @doc]
      )
    )
  )