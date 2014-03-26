(defproject secret-santa "0.1.0-SNAPSHOT"
  :description "Secret Santa for distributed friend groups"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.logic "0.8.7"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [lib-noir "0.7.9"]
                 [clj-time "0.6.0"]
                 [clojurewerkz/mailer "1.0.0"]
                 [com.novemberain/monger "1.5.0"]
                 [markdown-clj "0.9.41"]
                 [de.ubercode.clostache/clostache "1.3.1"]
                 [ring-server "0.3.0"]]
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler secret-santa.handler/app
         :init secret-santa.handler/init
         :destroy secret-santa.handler/destroy}
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.0"]]}})
