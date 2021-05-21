(ns demo.kernel
  (:require 
   [clojure.core.async :refer [<! <!! >! >!! put! chan close! go go-loop]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.default-config]  ; side-effects
   [picasso.kernel.clj] ; side-effects
   )
  )



(<!! 
 (kernel-eval {:kernel :clj :code "13"})
 ) 

(<!!
 (kernel-eval {:kernel :clj :code "(+ 1 1) [7 8]"}))
