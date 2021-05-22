(ns picasso.document.runner
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [picasso.document.eval :as eval]
   [picasso.document.transact :refer [fns-lookup transact]]))

(defonce doc (atom nil))

(defn run [fun-args]
  (reset! doc (transact @doc fun-args))
  nil)

(swap! fns-lookup assoc
       :eval-all (partial eval/eval-all run)
       :eval-segment (partial eval/eval-segment-id run))

(comment
  (require '[picasso.data.document])
  (reset! doc picasso.data.document/document)
  (run [:clear-all])
  (run [:remove-segment 2])
  (run [:set-state-segment 2 {:bongo :trott}])
  (run [:eval-all])
  (run [:eval-segment 2])

  @doc




 ; 
  )