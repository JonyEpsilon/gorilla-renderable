(ns pinkgorilla.doo-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]

   [pinkgorilla.ui.gorilla-renderable]
   ;[pinkgorilla.ui.rendererCLJS]

   [pinkgorilla.ui.core-test]
   [pinkgorilla.ui.custom-renderable-test]))



(doo-tests
 'pinkgorilla.ui.core-test
 'pinkgorilla.ui.custom-renderable-test)


;(require '[pinkgorilla.ui.gorilla-renderable])
;(require '[pinkgorilla.ui.rendererCLJS])
;(require '[pinkgorilla.ui.bongo])

;(require '[pinkgorilla.ui.table])


; Type Tests	array? fn? number? object? string?
; instance?
; 	fn?  ifn?


;(println "rendering Bongo")
;(println (pr-str (pinkgorilla.ui.bongo/Bongo. {:a 1 :b "2"})))
;(println (render (pinkgorilla.ui.bongo/Bongo. {:a 1 :b "2"})))

;(println "rendering string")
;(render "s")