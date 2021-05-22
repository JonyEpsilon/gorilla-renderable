(ns picasso.document.rf
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [re-frame.core :as rf]
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




(rf/reg-event-db
 :doc/modify
 (fn [db [_ id fun-kw & args]]
   
     (if-let [doc (get-in db [:docs id])]
      (assoc-in db [:docs id])
       (do (error ":doc/modify document not found:" id)
           db))
     (do 
         db))))