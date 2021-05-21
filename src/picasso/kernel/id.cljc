(ns picasso.kernel.id
  #?(:cljs
     (:require
      [cljs-uuid-utils.core :as uuid-cljs])))

(defn guuid []
  #?(:clj (-> (.toString (java.util.UUID/randomUUID)) keyword)
     :cljs  (-> (uuid-cljs/make-random-uuid) uuid-cljs/uuid-string keyword)))

(defonce current (atom 0))

(defn id []
  (swap! current inc)
  @current)
