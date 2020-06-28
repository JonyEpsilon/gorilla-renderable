(ns picasso.default-config
  "side effects for Renderable types"
  (:require
   [picasso.protocols]
   [picasso.paint.core]

   #?(:clj [picasso.render.clj-types])
   #?(:clj [picasso.render.image])

   #?(:cljs [picasso.render.cljs-types])
   #?(:cljs [picasso.render.list-like])))