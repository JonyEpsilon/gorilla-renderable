(ns ui.notebook.view.layout
  (:require
   [ui.codemirror.theme :refer [style-codemirror-fullscreen style-codemirror-inline]]
   ;[pinkgorilla.notebook-ui.completion.component :refer [completion-component]]
   [ui.notebook.view.segment-nav :refer [segment-nav]]
   [ui.notebook.view.segment :refer [segment-input segment-output segment-output-no-md]]))

(defn layout-vertical [segment]
  [:div.w-full ; .mt-5.p-2
   [segment-input segment]
   ;[segment-output eval-result]
   [segment-output-no-md segment]])

(defn layout-horizontal [segment]
  [:div.w-full.flex.flex-row ; .mt-5.p-2
   [:div {:class "w-1/2"}
    [segment-input segment]]
   [:div {:class "w-1/2"}
    [segment-output segment]]])

(defn layout-single [{:keys [id] :as segment}]
  [:div.flex.flex-row.w-full.h-full.min-h-full.bg-yellow-400.items-stretch
   [style-codemirror-fullscreen]
    ; LEFT: code-mirror / code-completion
   [:div.bg-yellow-500.h-full.flex.flex-col {:class "w-1/2"}
    [segment-nav]
    [:p "seg: " (pr-str segment)]
    [:div.w-full.bg-red-300.flex-grow
      ;{:style {:height "600px"}}
     [segment-input segment]]
    [:div.h-40.w-full.bg-teal-300
     ;[completion-component]
     [:p "completion component"]]]
      ; RIGHT: error / console / result
   [:div.bg-blue-100.h-full.flex.flex-col
    {:class "w-1/2"
     :style {:overflow-y "auto"}}
    [:div.flex-grow  ; flex-grow scales the element to remaining space
     [segment-output segment]]]])

(defn layout-multiple [layout segments]
  (let [segment-layout (case layout
                         :vertical   layout-vertical
                         :horizontal layout-horizontal
                         layout-vertical)]
    [:div.h-full.min-h-full
     [style-codemirror-inline]
     (for [s segments]
       ^{:key (:id s)}
       [segment-layout s])]))
;; => nil


(defn layout-stacked [segments]
  [:div.flex.flex-row.w-full.h-full.bg-yellow-400.stackednotebook.overflow-y-auto.max-h-full
   [:div.stackedinput.overscroll-y-auto.overflow-y-scroll {:class "w-1/2"}
    [style-codemirror-inline]
    (for [s segments]
      ^{:key (:id s)}
      [segment-input s])]
   [:div.stackedoutput.overscroll-y-auto.overflow-y-scroll {:class "w-1/2"}
    (for [s segments]
      ^{:key (:id s)}
      [segment-output s])]])
;; => nil


(defn notebook-layout [{:keys [layout]
                        :or {layout :vertical}
                        :as settings}
                       active-segment segments]
  (case layout
    :single
    [layout-single active-segment]

    :stacked
    [layout-stacked segments]

    [layout-multiple layout segments]))