(ns ui.notebook.core
  (:require
   [re-frame.core :as rf]
   [picasso.document.transactor] ; side-effects
   [picasso.document.position :refer [segment-active]]
   [ui.notebook.view.layout :refer [notebook-layout]]
   [ui.notebook.settings] ; side-effects
   [ui.notebook.view.menu :refer [menu]]))

(defn render-unknown [{:keys [id type data state] :as seg}]
  [:div.render-unknown
   [:p "segment id:" id "type " type]
   [:p "data: " (pr-str data)]
   [:p "state: " (pr-str state)]])

(defn seg-view [{:keys [md-view code-view] :as opts} {:keys [id type data state] :as seg}]
  (let [render (case type
                 :code code-view
                 :md md-view)]
    (if render
      [render id data state]
      [render-unknown seg])))

(defn doc-view
  [opts {:keys [id meta segments] :as document}]
  (let [seg-view (partial seg-view opts)]
    [:div
     [:p "meta: " (pr-str meta)]]
    (into [:div] (map seg-view segments))))

(defn template-header-document [header document]
  [:div {:style {:display "grid"
                 :height "100vh"
                 :width "100vw"
                 :grid-template-columns "auto"
                 :grid-template-rows "30px auto"}}
   header
   ;[:div.overflow-auto.m-0.p-0
   ; {:style {:background-color "red"
   ;          :height "100%"
    ;         :max-height "100%"}}
   document]
;  ]
  )

(defn notebook-view [opts]
  (let [doc (rf/subscribe [:notebook])
        layout (rf/subscribe [:notebook/layout])]
    (fn [opts]
      [template-header-document
       [menu]
       ;[:div.w-full.h-full.min-h-full.bg-gray-100 ; .overflow-scroll
       [notebook-layout
        (merge {:layout @layout} opts)
        (segment-active @doc)
        (:segments @doc)]])))

