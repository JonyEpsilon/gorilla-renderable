;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns pinkgorilla.ui.gorilla-renderable)

;;; This is the protocol that a type must implement if it wants to customise its rendering in Gorilla. It defines a
;;; single function, render, that should transform the value into a value that the front-end's renderer can display.
(defprotocol Renderable
  (render [self]))


;; awb99: prefixes are necessary, because only "name" would overwrite clojure/name.
;; This should not happen, but it does.

(defprotocol RenderableJS
  (js-name [self]) ; a unique name that identifies the JS renderer
  (js-dependencies [self]) ; a vector of dependency-urls that need to be loaded prior to rendering
  (js-render [self]) ; javascript function body that will do the render part
  (js-cleanup [self])
  ) ; javascript function body that will do cleanup when the render output is removed.
