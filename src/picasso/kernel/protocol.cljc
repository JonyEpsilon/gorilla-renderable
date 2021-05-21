(ns picasso.kernel.protocol
  (:require
   [picasso.kernel.id :refer [guuid]]
   #?(:clj [clojure.core.async :refer [<! <!! >! >!! put! chan close! go go-loop]]
      :cljs [cljs.core.async  :refer [<! <!! >! >!! put! chan close!]
             :refer-macros [go go-loop]])))

#?(:clj (defmulti kernel-eval (fn [e] (:kernel e)))
   :cljs (defmulti kernel-eval (fn [e] (:kernel e))))

(defmethod kernel-eval :default [m]
  (let [c (chan)]
    (go (<! c {:id (guuid)
               :error "kernel unknown"})
        (close! c))
    c))
