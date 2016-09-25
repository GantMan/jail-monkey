/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react'
import JailMonkey from 'jail-monkey'
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native'

class ExampleProject extends Component {

  componentWillMount () {
    this.isJailBroken = JailMonkey.isJailBroken().toString()
    this.canMockLocation = JailMonkey.canMockLocation().toString()
    this.trustFall = JailMonkey.trustFall().toString()
  }

  render () {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          isJailBroken: {this.isJailBroken}
        </Text>
        <Text style={styles.welcome}>
          canMockLocation: {this.canMockLocation}
        </Text>
        <Text style={styles.welcome}>
          trustFall: {this.trustFall}
        </Text>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF'
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5
  },
})

AppRegistry.registerComponent('ExampleProject', () => ExampleProject)
