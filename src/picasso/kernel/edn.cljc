(ns picasso.kernel.edn
  (:require
   [clojure.edn :refer [read-string]]
   #?(:clj  [clojure.core.async :refer [>! chan close! go]]
      :cljs [cljs.core.async :refer [>! chan close!] :refer-macros [go]])
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [picasso.id :refer [guuid]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.converter :refer [->picasso]]))

(defmethod kernel-eval :edn [{:keys [id code]
                              :or {id (guuid)}}]
  (let [c (chan)]
    (info "edn-eval: " code)
    (go (try (let [eval-results (read-string code)
                   _ (info "eval result: " eval-results)
                   picassos (->picasso eval-results)
                   _ (info "picassos: " picassos)
              ;(into [] (map ->picasso eval-results))
                   ]
               (>! c {:id id
                      :picasso picassos}))

             (catch #?(:cljs js/Error :clj Exception) e
               (error "eval ex: " e)
               (>! c {:id id
                      :error e})))
        (close! c))
    c))




