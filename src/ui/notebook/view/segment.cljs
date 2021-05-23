(ns ui.notebook.view.segment
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :as rf :refer [subscribe dispatch]]
   [ui.notebook.view.segment.markdown :refer [md-segment]]
   [ui.notebook.view.segment.eval-result :refer [er-segment]]
   [ui.notebook.view.segment.code  :refer [segment-code]]))

;; input

(defn unknown-segment [nb-settings {:keys [id type] :as seg}]
  [:div.bg-red-700
   [:p "seg-id: " id  " - unknown type: " type]
   [:p "data: " (pr-str seg)]])

(defn segment-input [nb-settings {:keys [type] :as seg}]
  (case type
    :md [:div] ; [segment-md nb-settings seg]
    :code [segment-code nb-settings seg]
    [unknown-segment nb-settings seg]))

;; output

(defn segment-output [nb-settings {:keys [type] :as seg}]
  (case type
    :md [md-segment nb-settings seg]
    :code [er-segment nb-settings seg]
    [unknown-segment nb-settings seg]))

