(ns demo.paint
  (:require
   [picasso.default-config]
   [picasso.protocols :refer [paint render]]
   [picasso.data.paint :as data]
   ))

(render 1)

(paint data/hiccup)

(paint data/list-like)
 
