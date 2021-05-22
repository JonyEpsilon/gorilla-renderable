(ns picasso.kernel.edn
  (:require
   [clojure.edn :refer [read-string]]
   [cljs.core.async :refer [>! chan close! go]]
   [picasso.id :refer [guuid]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.converter :refer [->picasso]]))

(defmethod kernel-eval :edn [{:keys [id code]
                              :or {id (guuid)}}]
  (let [c (chan)]
    (println "edn-eval: " code)
    (go (try (let [eval-results (read-string code)
                   _ (println "eval result: " eval-results)
                   picassos (->picasso eval-results)
                   _ (println "picassos: " picassos)
              ;(into [] (map ->picasso eval-results))
                   ]
               (>! c {:id id
                      :picasso picassos}))

             (catch js/Error e
               (println "eval ex: " e)
               (>! c {:id id
                      :error e})))
        (close! c))
    c))




