(ns picasso.document.rf
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [re-frame.core :as rf]
   [picasso.document.core :as edit]
   [picasso.document.eval :as eval]
   ))


(rf/reg-event-db
 :doc/add
 (fn
   [db [_ {:keys [id] :as document}]]
   (let [db (if (:docs db)
              db
              (assoc db :docs {}))]
     (debugf "Adding document: %s " id)
     (assoc-in db [:docs id] document))))

(rf/reg-sub
 :doc/view
 (fn [db [_ id]]
   (get-in db [:docs id])))


(def fun-lookup
  {:clear-all edit/clear-all
   :eval-all eval/eval-all
   })

(rf/reg-event-db
 :doc/modify
 (fn [db [_ id fun-kw & args]]
   (if-let [fun (fun-kw fun-lookup)]
     (if-let [doc (get-in db [:docs id])]
       ;(if args
         ;(apply fun doc args)
          (->> (fun doc)
               (assoc-in db [:docs id]))
         ;)
       (do (error ":doc/modify document not found:" id)
           db))
     (do (error ":doc/modify fn not found:" fun-kw)
         db))))