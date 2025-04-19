import { useState } from 'react'
import '@mantine/core/styles.css';
import { Group, Button, MantineProvider, createTheme, TextInput } from '@mantine/core';
import './styles.css'

const theme = createTheme({
  colors: {
    'ocean-blue': ['#7AD1DD', '#5FCCDB', '#44CADC', '#2AC9DE', '#1AC2D9', '#11B7CD', '#09ADC3', '#0E99AC', '#128797', '#147885'],
    'bright-pink': ['#F0BBDD', '#ED9BCF', '#EC7CC3', '#ED5DB8', '#F13EAF', '#F71FA7', '#FF00A1', '#E00890', '#C50E82', '#AD1374'],
  },
});

function App() {
  return (
    <div className="disable-caret">
      <MantineProvider theme={theme}>
        <div className="container">
          <TextInput
            variant="unstyled"
            size="md"
            placeholder="Input placeholder"
          />
        </div>
      </MantineProvider>
        
    </div>
  );
}

export default App;