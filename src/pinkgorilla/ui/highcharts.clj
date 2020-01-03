(ns pinkgorilla.ui.highcharts
  "plugin to render vega-charts in pink-gorilla"
  (:require
   [pinkgorilla.ui.gorilla-renderable :refer :all] ; define Renderable (which has render function)
   [pinkgorilla.ui.module-test :refer [module-test]] ;; simple JSON dumper for testing
   ))

;; UI Modules use RequireJS.
;; Require-JS configuration has to be done centrally.
;; The follwing RequireJS modules are defined in PinkGorilla Notebook
;; highcharts

 ;(js/Highcharts.Chart. (reagent/dom-node this) (clj->js (make-chart-config data))))

(def module "
  define([], function () {
      return {
         version: 'highcharts 0.0.2',
         render: function (id_or_domel, data) {
            var selector_or_domel = id_or_domel;
            if (typeof id_or_domel === 'string' || id_or_domel instanceof String) {
               selector_or_domel = '#'+ id_or_domel;
               console.log ('highcharts-module is rendering to selector id: ' + selector_or_domel);
            } else {
               console.log ('highcharts-module is rendering to dom-element');
            }
            var dataJson = JSON.stringify(data)
            console.log ('highcharts-module data: ' + dataJson);
            require(['highcharts'], function(highcharts) {
              new highcharts.Chart (selector_or_domel, data, {defaultStyle:true}).catch(console.warn);
              }, function(err) {
                console.log('Failed to load');
            });
         }
      }
  });
")

(defn highchart! [spec]
  "renders highchart spec to a gorilla cell"
  (reify Renderable
    (render [_]
      {:type :jsscript
       :content
       {:data spec
          ;:module module-test
        :module module}
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))

(comment
  (render (highchart! {:bongo 1})))


