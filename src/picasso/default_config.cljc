(ns picasso.default-config
  "side effects for Renderable types"
  (:require
   [picasso.protocols]

   ; render
   #?(:clj [picasso.render.clj-types])
   #?(:clj [picasso.render.image])
   #?(:cljs [picasso.render.cljs-types])

   ; paint
   [picasso.paint.default]
   [picasso.paint.hiccup]
   [picasso.paint.list-like]

   ; kernel
   #?(:clj [picasso.kernel.clj])
   #?(:cljs [picasso.kernel.edn])
   ;
   ))