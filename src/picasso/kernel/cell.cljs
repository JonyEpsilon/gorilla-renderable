(ns picasso.kernel.cell
  (:require
   [cljs.core.async :refer [<!] :refer-macros [go]]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [picasso.kernel.picasso :refer [picasso-result]]
   [picasso.kernel.protocol :refer [kernel-eval]]))

(defn eval-cell [e]
  (let [er (r/atom e)]
    (go  (let [ker (<! (kernel-eval e))]
           (reset! er ker)))
    (fn [e]
      (let [er @er
            picasso (when er (:picasso er))]
        (println "er: " er)
        (if picasso
          [picasso-result picasso]
          [:p "evaluating.."])))))