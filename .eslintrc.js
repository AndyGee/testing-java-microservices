module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: ["plugin:vue/essential", "@vue/prettier"],
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-mixed-spaces-and-tabs": [2, "smart-tabs"],
    "no-unused-vars": "off",
    "no-undef": "off",
    "max-len": ["warn", {"code": 180}],
    "multiline-ternary": ["error", "always-multiline"]
  },
  parserOptions: {
    parser: "babel-eslint"
  }
};
