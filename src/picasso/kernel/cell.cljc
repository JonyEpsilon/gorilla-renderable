(ns picasso.kernel.cell
  (:require
   #?(:clj  [clojure.core.async :refer [<! go]]
      :cljs [cljs.core.async :refer [<!] :refer-macros [go]])
   #?(:cljs [reagent.core :as r])
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [picasso.kernel.picasso :refer [picasso-result]]
   [picasso.kernel.protocol :refer [kernel-eval]]))

(defn eval-cell [e]
  (let [er #?(:clj (atom e)
              :cljs (r/atom e))]
    (go  (let [ker (<! (kernel-eval e))]
           (reset! er ker)))
    (fn [e]
      (let [er @er
            picasso (when er (:picasso er))]
        (info "er: " er)
        (if picasso
          [picasso-result picasso]
          [:p "evaluating.."])))))