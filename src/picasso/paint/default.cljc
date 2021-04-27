(ns picasso.paint.default
  (:require
   [picasso.protocols :refer [paint]]))

(defmethod paint :default [{:keys [type] :as picasso-spec}]
  (println "painter not found: " picasso-spec)
  [:div.bg-red-300.border-solid
   [:h1 (str "Error! Painter [" type "] not found!")]
   [:p (pr-str picasso-spec)]])



