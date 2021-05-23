(ns picasso.document.eval
  (:require
   [picasso.id :refer [guuid]]
   #?(:clj  [clojure.core.async :refer [<! go]]
      :cljs [cljs.core.async :refer [<!] :refer-macros [go]])
   [taoensso.timbre :refer [trace debug info error]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.document.core :as edit]))

(defn eval-segment [exec {:keys [id data] :as seg}]
  (info "eval seg: " seg)
  (let [eval-id (guuid)
        ev (assoc data :id eval-id)]
    (info "eval data: " ev)
    (exec [:clear-segment id])
    (go (let [er (<! (kernel-eval ev))]
          (info "setting er id: " id "er: " er)
          (exec [:set-state-segment id er])))))

(defn eval-segment-id [run {:keys [segments] :as doc} id]
  (if-let [seg (edit/get-segment doc id)]
    (eval-segment run seg)
    (error "segment not found"))
  doc)

(defn eval-all [exec {:keys [segments] :as doc}]
  (let [clear-segment #(exec [:clear-segment %])
        eval-segment (partial eval-segment exec)
        code-segments (->> segments
                           (filter #(= :code (:type %))))]
    (info "eval all. count: " (count code-segments))
    (doall (map clear-segment code-segments))
    (doall (map eval-segment code-segments))
    doc))