(ns picasso.paint.default
  (:require
   [picasso.protocols :refer [paint]]))

(defmethod paint :default [{:keys [type] :as picasso-spec}]
  (println "painter not found: " picasso-spec)
  [:div.bg-red-300.border-solid
   [:h1 (str "Painter not found: type: [" type "] ")]
   [:p (pr-str picasso-spec)]])



