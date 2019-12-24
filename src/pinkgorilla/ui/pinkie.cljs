(ns pinkgorilla.ui.pinkie
  (:require
   [goog.object :as gobj]
   [reagent.core :as r :refer [atom]]
   [reagent.impl.template]))


(def custom-renderers (atom {}))


(defn register-tag [k v]
  (swap! custom-renderers assoc k v)
  (gobj/set reagent.impl.template/tag-name-cache (name k) v))
