(ns picasso.kernel.clj
  (:require
   [clojure.core :refer [read-string load-string]]
   [clojure.core.async :refer [<! <!! >! >!! put! chan close! go go-loop]]
   [picasso.kernel.id :refer [guuid]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.converter :refer [->picasso]]))

(defmethod kernel-eval :clj [{:keys [id code]
                              :or {id (guuid)}}]
  (let [c (chan)]
    (println "clj-eval: " code)
    (go (try (let [eval-results (load-string code)
                   ;eval-results (read-string code)
                   _ (println "eval result: " eval-results)
                   picassos (->picasso eval-results)
                   _ (println "picassos: " picassos)
              ;(into [] (map ->picasso eval-results))
                   ]
               (>! c {:id id
                      :picasso picassos}))

             (catch Exception e
               (println "eval ex: " e)
               (>! c {:id id
                      :error e})))
        (close! c))
    c))




