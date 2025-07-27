import React from 'react'
import classNames from 'classnames'

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode
  variant?: 'primary' | 'danger' | 'default'
  size?: 'sm' | 'md'
}

export const Button = ({
  children,
  variant = 'default',
  size = 'md',
  className,
  ...rest
}: ButtonProps) => {
  const base =
    'rounded-pill font-semibold focus:outline-none transition-colors disabled:opacity-50'

  const variantStyles = {
    default: 'bg-gray-200 text-gray-800 hover:bg-gray-300',
    primary: 'bg-primary text-white hover:bg-indigo-700',
    danger: 'bg-red-500 text-white hover:bg-red-600',
  }

  const sizeStyles = {
    sm: 'px-3 py-1 text-sm',
    md: 'px-4 py-2 text-base',
  }

  return (
    <button
      className={classNames(base, variantStyles[variant], sizeStyles[size], className)}
      {...rest}
    >
      {children}
    </button>
  )
}
