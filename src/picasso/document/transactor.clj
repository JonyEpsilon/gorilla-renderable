(ns picasso.document.transactor
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [picasso.document.eval :as eval]
   [picasso.document.transact :refer [fns-lookup transact]]))

(defonce doc (atom nil))

(defn exec [fun-args]
  (reset! doc (transact @doc fun-args))
  nil)

(swap! fns-lookup assoc
       :eval-all (partial eval/eval-all exec)
       :eval-segment (partial eval/eval-segment-id exec))

(comment
  (require '[picasso.data.document])
  (reset! doc picasso.data.document/document)
  (exec [:clear-all])
  (exec [:remove-segment 2])
  (exec [:set-state-segment 2 {:bongo :trott}])
  (exec [:eval-all])
  (exec [:eval-segment 2])

  @doc


 ; 
  )