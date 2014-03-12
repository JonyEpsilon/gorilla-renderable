;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns gorilla-renderable.core)

;;; This is the protocol that a type must implement if it wants to customise its rendering in Gorilla. It defines a
;;; single function, render, that should transform the value into a value that the front-end's renderer can display.
(defprotocol Renderable
  (render [self]))
