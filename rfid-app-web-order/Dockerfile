# pull official base image
FROM node:12.19.0-buster-slim

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./

RUN npm config set unsafe-perm true
RUN npm install --production
RUN npm install react-scripts@3.4.1 -g
# add app
COPY . ./

# start app
CMD ["npm", "start"]
