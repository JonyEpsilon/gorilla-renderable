(ns ui.notebook.view.layout
  (:require
   [ui.codemirror.theme :refer [style-codemirror-fullscreen style-codemirror-inline]]
   ;[pinkgorilla.notebook-ui.completion.component :refer [completion-component]]
   [ui.notebook.view.segment-nav :refer [segment-nav]]
   [ui.notebook.view.segment :refer [segment-input segment-output]]))

(defn layout-vertical [nb-settings seg]
  [:div.w-full ; .mt-5.p-2
   [segment-input nb-settings seg]
   [segment-output nb-settings seg]])

(defn layout-horizontal [nb-settings seg]
  [:div.w-full.flex.flex-row ; .mt-5.p-2
   [:div {:class "w-1/2"}
    [segment-input nb-settings seg]]
   [:div {:class "w-1/2"}
    [segment-output nb-settings seg]]])

(defn layout-single [nb-settings {:keys [id] :as seg}]
  [:div.flex.flex-row.w-full.h-full.min-h-full.bg-yellow-400.items-stretch
   [style-codemirror-fullscreen]
    ; LEFT: code-mirror / code-completion
   [:div.bg-yellow-500.h-full.flex.flex-col {:class "w-1/2"}
    [segment-nav]
    [:div.w-full.bg-red-300.flex-grow
      ;{:style {:height "600px"}}
     [segment-input nb-settings seg]]
    [:div.h-40.w-full.bg-teal-300
     ;[completion-component]
     [:p "completion component"]]]
      ; RIGHT: error / console / result
   [:div.bg-blue-100.h-full.flex.flex-col
    {:class "w-1/2"
     :style {:overflow-y "auto"}}
    [:div.flex-grow  ; flex-grow scales the element to remaining space
     [segment-output nb-settings seg]]]])

(defn layout-multiple [nb-settings segments]
  (let [segment-layout (case (:layout nb-settings)
                         :vertical   layout-vertical
                         :horizontal layout-horizontal
                         layout-vertical)]
    [:div.h-full.min-h-full
     [style-codemirror-inline]
     (for [seg segments]
       ^{:key (:id seg)}
       [segment-layout nb-settings seg])]))
;; => nil


(defn layout-stacked [nb-settings segments]
  [:div.flex.flex-row.w-full.h-full.bg-yellow-400.stackednotebook.overflow-y-auto.max-h-full
   [:div.stackedinput.overscroll-y-auto.overflow-y-scroll {:class "w-1/2"}
    [style-codemirror-inline]
    (for [seg segments]
      ^{:key (:id seg)}
      [segment-input nb-settings seg])]
   [:div.stackedoutput.overscroll-y-auto.overflow-y-scroll {:class "w-1/2"}
    (for [seg segments]
      ^{:key (:id seg)}
      [segment-output nb-settings seg])]])
;; => nil


(defn notebook-layout [{:keys [layout]
                        :or {layout :vertical}
                        :as nb-settings}
                       active-segment segments]
  (case layout
    :single
    [layout-single nb-settings active-segment]

    :stacked
    [layout-stacked nb-settings segments]

    [layout-multiple nb-settings segments]))