(ns ui.notebook.view.segment.code
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :as rf]
   [ui.code.goldly.codemirror-themed  :refer [codemirror-themed]]
   ))


(defn segment-input-code [{:keys [id data segment-active?] :as cm-opt} segment]
  [:div {:style {:position "relative"}} ; container for kernel absolute position
   ; kernel - one color for both dark and light themes.
   (when-let [kernel (:kernel data)] ; snippets might not define kernel
     [:span.pr-2.text-right.text-blue-600.tracking-wide.font-bold.border.border-blue-300.rounded
      {:on-click #(rf/dispatch [:segment-active/kernel-toggle])
       :style {:position "absolute"
               :z-index 200 ; dialog is 1040 (we have to be lower)
               :top "2px" ; not too big, so it works for single-row code segments
               :right "10px"
               :width "50px"
               :height "22px"}} kernel])
   (if segment-active?
     [codemirror-themed id cm-opt]
     [codemirror-themed id cm-opt])])