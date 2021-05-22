(ns demo.kernel
  (:require
   [clojure.core.async :refer [<! <!! go]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   ; side-effects
   [picasso.default-config]))

(defn eval-clj [code]
  (go
    (let [er  (<! (kernel-eval {:kernel :clj :code code}))]
      (println "er:" er))))

(eval-clj "13")

(eval-clj "(+ 1 1) [7 8]")
