(ns picasso.document.eval
  (:require
   [picasso.id :refer [guuid]]
   #?(:clj  [clojure.core.async :refer [<! go]]
      :cljs [cljs.core.async :refer [<!] :refer-macros [go]])
   
   [picasso.kernel.picasso :refer [picasso-result]]
   [picasso.kernel.protocol :refer [kernel-eval]]))


(defn eval-segment [{:keys [id data] :as seg}]
  (println "eval seg: " seg)
  (let [eval-id (guuid)
        ev (assoc data :id eval-id)
        ]
    (println "eval data: " ev)
    (go (let [er (<! (kernel-eval ev))]
          (println "er: " er)
              
              ))))


(defn eval-all [{:keys [segments] :as doc}]
  (let [clean-code-segments (->> segments
                                 (filter #(= :code (:type %)))
                                 (map #(assoc % :state {})))]
    (println "eval all. count: " (count clean-code-segments) clean-code-segments)
    (doall (map eval-segment clean-code-segments))
    (assoc doc :segments clean-code-segments)
         ))