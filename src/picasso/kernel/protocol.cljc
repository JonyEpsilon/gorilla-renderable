(ns picasso.kernel.protocol
  (:require
   [picasso.kernel.id :refer [guuid]]
   #?(:clj [clojure.core.async :refer [>!  chan close! go]]
      :cljs [cljs.core.async  :refer [>! chan close!]
             :refer-macros [go]])))

#?(:clj (defmulti kernel-eval (fn [e] (:kernel e)))
   :cljs (defmulti kernel-eval (fn [e] (:kernel e))))

(defmethod kernel-eval :default [m]
  (let [c (chan)]
    (go (>! c {:id (guuid)
               :error "kernel unknown"})
        (close! c))
    c))
