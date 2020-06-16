(ns picasso.paint.core
  (:require
   [picasso.protocols :refer [paint]]))

(defmethod paint :hiccup [picasso-spec]
  (:content picasso-spec))

(defmethod paint :reagent [picasso-spec]
  (:content picasso-spec))

(defmethod paint :default [picasso-spec]
  [:h1 (str "Error! No painter found for:" picasso-spec)])