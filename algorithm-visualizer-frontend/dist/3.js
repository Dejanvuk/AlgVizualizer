(window.webpackJsonp=window.webpackJsonp||[]).push([[3],{477:function(e,t,n){"use strict";var a=n(2),r=n(3),o=n(6),i=n.n(o),l=n(0),c=n.n(l),s=n(7),p=c.a.forwardRef((function(e,t){var n=e.bsPrefix,o=e.size,l=e.toggle,p=e.vertical,u=e.className,m=e.as,d=void 0===m?"div":m,b=Object(r.a)(e,["bsPrefix","size","toggle","vertical","className","as"]),g=Object(s.b)(n,"btn-group"),h=g;return p&&(h=g+"-vertical"),c.a.createElement(d,Object(a.a)({},b,{ref:t,className:i()(u,h,o&&g+"-"+o,l&&g+"-toggle")}))}));p.displayName="ButtonGroup",p.defaultProps={vertical:!1,toggle:!1,role:"group"},t.a=p},484:function(e,t,n){var a=n(485);"string"==typeof a&&(a=[[e.i,a,""]]);var r={insert:"head",singleton:!1};n(70)(a,r);a.locals&&(e.exports=a.locals)},485:function(e,t,n){(e.exports=n(69)(!1)).push([e.i,".or-seperator {\n    margin-top:5px;\n    margin-bottom:5px;\n    width:100%;\n    border-top:1px solid gray;\n}\n\n.social-btn {\n    width: 250px;\n}\n\n.btnOauth2 {\n    display: block;\n    margin-top: 5px;\n    margin-bottom: 5px;\n\n    background: white;\n    color: #444;\n    border-radius: 5px;\n    border: thin solid #888;\n    box-shadow: 1px 1px 1px grey;\n    white-space: nowrap;\n}\n\n.btnOauth2:hover {\n    cursor: pointer;\n  }\n\n#btnGoogle {\n    background-image: url(/images/google-logo.png);\n    background-repeat: no-repeat;\n    background-position: left;\n    padding-left: 40px;\n}\n\n#btnFacebook {\n    background-image: url(/images/facebook-logo.png);\n    background-repeat: no-repeat;\n    background-position: left;\n    padding-left: 40px;\n}\n\n#btnGithub {\n    background-image: url(/images/github-logo.png);\n    background-repeat: no-repeat;\n    background-position: left;\n    padding-left: 40px;\n}",""])},489:function(e,t,n){"use strict";n.r(t);var a=n(16),r=n.n(a),o=n(0),i=n.n(o),l=n(150),c=n(477),s=n(54),p=n(46),u=n(149),m=n(62);n(484);function d(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}class b extends o.Component{constructor(e){super(e),this.state={item:{name:"",username:"",email:"",password:""}}}handleChange(e){let t=function(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?d(n,!0).forEach((function(t){r()(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):d(n).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}({},this.state.item);t[e.target.name]=e.target.value,this.setState({item:t})}async handleSubmit(e){e.preventDefault();const{item:t}=this.state;await fetch(u.b+"/auth/sign-up",{method:"POST",headers:{Accept:"application/json","Content-Type":"application/json"},body:JSON.stringify(t)}).then(()=>{this.props.history.push("/")})}render(){return i.a.createElement("div",{className:"login-container"},i.a.createElement(l.a,{onSubmit:this.handleSubmit.bind(this)},i.a.createElement("p",{className:"hint-text"},"Sign up with your social media account or email address"),i.a.createElement(c.a,{className:"social-btn text-center btn-group-vertical btn-group-justified"},i.a.createElement(s.a,{id:"btnFacebook",variant:"",className:"btnOauth2",onClick:()=>{window.location.href=u.c+"facebook?redirect_uri="+u.d}},"Facebook"),i.a.createElement(s.a,{id:"btnGoogle",variant:"",className:"btnOauth2",onClick:()=>{window.location.href=u.c+"google?redirect_uri="+u.d}},"Google"),i.a.createElement(s.a,{id:"btnGithub",variant:"",className:"btnOauth2",onClick:()=>{window.location.href=u.c+"github?redirect_uri="+u.d}},"Github")),i.a.createElement("div",{className:"d-flex"},i.a.createElement("hr",{className:"my-auto flex-grow-1"}),i.a.createElement("div",{className:"px-4"},"or"),i.a.createElement("hr",{className:"my-auto flex-grow-1"})),i.a.createElement("h2",null,"Create an Account"),i.a.createElement(l.a.Group,null,i.a.createElement(l.a.Control,{placeholder:"Name",name:"name",onChange:this.handleChange.bind(this)})),i.a.createElement(l.a.Group,null,i.a.createElement(l.a.Control,{placeholder:"Username",name:"username",onChange:this.handleChange.bind(this)})),i.a.createElement(l.a.Group,null,i.a.createElement(l.a.Control,{type:"email",placeholder:"Email",name:"email",onChange:this.handleChange.bind(this)})),i.a.createElement(l.a.Group,null,i.a.createElement(l.a.Control,{type:"password",placeholder:"*******",name:"password",required:"required",onChange:this.handleChange.bind(this)})),i.a.createElement(l.a.Group,null,i.a.createElement(s.a,{variant:"primary",className:"btn btn-success btn-lg btn-block signup-btn",type:"submit"},"Submit"))),i.a.createElement("div",{className:"text-center"},"Already have an account? Log in ",i.a.createElement(p.b,{to:"/login"},"here")))}}t.default=Object(m.g)(b)}}]);
//# sourceMappingURL=3.js.map