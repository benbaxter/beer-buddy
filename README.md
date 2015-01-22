# :beer: beer-buddy :beer:

This is a personal beer ranking/rating system. It is also going to serve as a sample app for NKU's CSC 439/539 course. 

This application is built with spring-boot, angular, and material design

If you have any suggestions to make this a better application, please feel free to contact me or create an issue.

#Getting Started
From beer-buddy/beer-buddy project run the following maven commands:
<pre>mvn clean install</pre>
In eclipse, or IDE of choice, run the java main application in beer-buddy/beer-buddy-web/src/test/com/beerbuddy/web/apprunner/AppRunner
Another alternative is to look at spring-boots cli.


#TODO's and Road Map
These are just some features that would be nice to add. Dare I call this a backlog???  Currently the app is really basic...
- [ ] Prioritize backlog
- [ ] Define MVP
- [ ] Front end performance 
  - [ ] Clean out material-design-icons to reduce build time
  - [ ] Create bower dependency file
  - [ ] Create gulp script 
	- [ ] automate bower dependency installs for bootstrapping the project
    - [ ] minify and compress js code
- [ ] Modularize architecture
  - [x] Create a master pom
  - [x] Create a core project
  - [ ] Create a security project
  - [ ] :question: Investigate if it makes sense to extract the dao and supply an h2 and mySql implementation as seperate modules. (Local vs. Enviornment)
- [ ] Sync process needs to reject beers that have invalid images.
- [ ] Admin stuff
  - [ ] Create a queue-like-request system to where users can request to add beers to the list.
  - [ ] Need to have an admin section that can rerun the sync process.
  - [ ] Add ability for 'admin' users to review submitted new beer requests and approve/edit beer attributes.
- [ ] Security
  - [ ] Use spring-security fully and not just implement it manually...
          Currently implementing their interfaces but need to hook it up with thier configuration adapters
  - [ ] Support @Secured annotation on rest apis.
  - [ ] Use oauth
    - [ ] Google login
    - [ ] Facebook login
- [ ] Social
  - [ ] Tweet on titter?
  - [ ] Facebook sharing? posting?
- [ ] Add data visualization
  - [ ] Info-graphic like representation of how popular a beer is
- [ ] Add list of users and scan their rankings of beers
- [ ] Compare your ranking list with others
- [ ] Add search abilities on beer. :question:
  - [ ] Contextual search like google?
  - [ ] Or advanced search options based on fields? 
  - [ ] Why not both?
- [ ] Multiple Views. (With an rest api, it would be nice to build ...)
  - [ ] Native android app
- [ ] Gamify app
  - [ ] Add badge system
  - [ ] Add levels
- [ ] Deploy to cloud service
  - [ ] Create persistant database (mySql:question:)
- [ ] Convert from maven to gradle
- [ ] Create sample test data to use for local development
- [ ] CI
  - [ ] Selenium end-to-end scripts
  - [ ] Setup Jenkins build system

Thanks for checking out beer buddy! Once it is hosted, I will provide a url 

**Cheers!** :beers:
