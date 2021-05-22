(ns picasso.kernel.clj
  (:require
   [clojure.core :refer [read-string load-string]]
   [clojure.core.async :refer [>! chan close! go]]
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [picasso.id :refer [guuid]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.converter :refer [->picasso]]))

(defmethod kernel-eval :clj [{:keys [id code]
                              :or {id (guuid)}}]
  (let [c (chan)]
    (info "clj-eval: " code)
    (go (try (let [eval-results (load-string code)
                   ;eval-results (read-string code)
                   ;_ (info "eval result: " eval-results)
                   picasso (->picasso eval-results)
                   ;_ (info "picasso: " picasso)
              ;(into [] (map ->picasso eval-results))
                   ]
               (>! c {:id id
                      :picasso picasso}))
             (catch Exception e
               (info "eval ex: " e)
               (>! c {:id id
                      :error e})))
        (close! c))
    c))




