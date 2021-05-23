(ns picasso.document.transact
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [picasso.document.core :as edit]))

(defonce fns-lookup
  (atom {:add-md edit/add-md
         :add-code edit/add-code
         :remove-segment edit/remove-segment
         :clear-all edit/clear-all
         :clear-segment edit/clear-segment
         :set-code-segment edit/set-code-segment
         :set-md-segment edit/set-md-segment
         :set-kernel-segment edit/set-kernel-segment
         :set-state-segment edit/set-state-segment}))

(defn transact [doc [fun-kw & args]]
  (if-let [fun (if (keyword? fun-kw)
                 (fun-kw @fns-lookup)
                 fun-kw)]
    (let [fun-kw (if (keyword? fun-kw)
                   fun-kw
                   :custom-function)]
      (if args
        (do (info "transact fun " fun-kw "args: " args)
            (apply fun doc args))
        (do (info "transact fun fun-kw")
            (fun doc))))
    (do (error "transact fn not found:" fun-kw)
        doc)))